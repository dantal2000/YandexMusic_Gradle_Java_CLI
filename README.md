
# Yandex Music Loader
**YML** is a user-side utilit for downloading the music from *YM * service.

> BEWARE! 
> 
> *All rights* for musical compositions belong to *their authors* and the company "*Yandex*"

# Building 
## Preparations
#### You will need

 1. <i>[JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)</i>  with version more or equal than <b>1. 8</b>
 2. <i>[Gradle](https://gradle.org/install/)</i>  with version more or equal than <b>4. 2. 1</b>

## Step 1 : Clone this repository
```
git clone https://github.com/dantal2000/YandexMusic_Gradle_Java_CLI.git
```
or just download the zip-archive and extract it

## Step 2 : Assemble the project with Gradle
When you are in the root of the project files type
```
gradle assembleDist
```

## Step 3 : Extract distribution archive
In directory `build/distributions` you will be able to see some archives. Get one of them and extract on what you want folder.

# Usage
After you build or download a realese of the project and extract it on what you want folder, you can start usage of this app. Rules are simple: when you are in root of extracted archive just type
```
bin/MusicLoaderLauncher --id <id>
```
where **id** is the music's id on service "YM". Another options still in development.


> Written with [StackEdit](https://stackedit.io/).
