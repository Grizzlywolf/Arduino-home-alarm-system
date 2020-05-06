from pusher_push_notifications import PushNotifications
import pyrebase
import threading
#from pathlib import Path
#data_folder = Path("C:/Users/Grizzlywolf/Documents/PythonFirebaseskripta/alarmni-sustav-firebase-adminsdk-33d3b-b190b7fac9.json")

config = {
  'apiKey': "AIzaSyCKeyClFKRjvPAqYQpI6XzTMLU1fJef-R3",
  'authDomain': "alarmni-sustav.firebaseapp.com",
  'databaseURL': "https://alarmni-sustav.firebaseio.com",
  'projectId': "alarmni-sustav",
  'storageBucket': "alarmni-sustav.appspot.com",
  'messagingSenderId': "65543855217",
  'appId': "1:65543855217:web:97749f3e4816929d",
  #'serviceAccount': data_folder
}
firebase = pyrebase.initialize_app(config)
db = firebase.database()

beams_client = PushNotifications(
    instance_id='da14c153-e2fa-4db8-9b1b-d4931cc98d22',
    secret_key='EEF5964194CDCA297D581C1ABA5CA12069189904DDF241728386E74E07FAB027',
)


def notifikator():
  MotionSensor = db.child("MotionSensor").child("detection").get()
  if MotionSensor.val() is 1:
    threading.Timer(60.0, notifikator).start()
    response = beams_client.publish_to_interests(
      interests=['hello'],
      publish_body={
        'apns': {
          'aps': {
            'alert': 'Upozorenje!',
          },
        },
        'fcm': {
          'notification': {
            'title': 'Upozorenje',
            'body': 'Detektiran pokret!',
          },
        },
      },
    )
  elif MotionSensor.val() is 0:
    threading.Timer(5.0, notifikator).start()
notifikator()