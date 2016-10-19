#!/bin/bash

# Fix the CircleCI path
function getAndroidSDK {
  export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$PATH"

  DEPS="$ANDROID_HOME/installed-dependencies"

  if [ ! -e $DEPS ]; then
    echo y | android update sdk -u -a -t android-19 &&
    echo y | android update sdk --no-ui --all --filter android-24 &&
    echo y | android update sdk --no-ui --filter build-tools-24.0.3 &&
    echo y | android update sdk -u -a -t platform-tools &&
    echo y | android update sdk -u --no-ui --filter "extra-android-m2repository" &&
    echo y | android update sdk -u -a -t "extra-android-support" &&
    echo y | android update sdk -u --no-ui --filter "extra-google-m2repository" &&
    echo no | android create avd -n testAVD -f -t android-24 --abi google_apis/armeabi-v7a &&
    touch $DEPS
  fi
}

