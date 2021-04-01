/*
 *  Date Created  :  7th November 2K19, 12:00 PM..!!
 *  Last Modified :  1st April 2K21,    07:20 PM..!!
 *
 *  <| ================================================================ |>
 *
 *  Project Title: KOH-Std-Lib
 *
 *  Latest Version: 0.0.4
 *
 *  Description:
 *  This project is a Java-Library with Utilities to ease up the tasks.
 *
 *  <| ================================================================ |>
 *
 *  Change Log:
 *
 *  <| ================================================================ |>
 *
 *  5th Commit - [Version 0.0.4]
 *
 *  1. Added following new classes:
 *      Pojos:  1. FileSizePojo         2. FilePojo         3. DirPojo      4. DirMetaDataPojo
 *      Enums:  1. HashAlgorithm        2. StandardFileSize 3. KeysManager
 *      Utils:  1. DirectorySizeFinder  2. HashGenerator    3. JsonHelper
 *  2. Moved StringOptions & TimeUnit to enums package
 *  3. Refactored from NOWHITESPACE to NO_WHITESPACE in StringOptions enum
 *  4. Changed from JDK 11 to 8 due to compatibility issues with Android (sourceCompatibility = 1.8)
 *
 *  <| ================================================================ |>
 *
 *  4th Commit - [Version 0.0.3]
 *
 *  1. Updated following methods to be public static :
 *      KOHStringUtil - wannaTryAgain, doesBeginsWithWhiteSpace, doesContainsOnlyWhiteSpace
 *      KOHFilesUtil  - deleteFileNow, renameFileNameToStr
 *  2. Moved createNewDir method from KOHStringUtil to KOHFilesUtil
 *
 *  <| ================================================================ |>
 *
 *  3rd Commit - [Version 0.0.2]
 *
 *  1. Updated packages from dev.koh.stdlib.* to stdlib.*
 *  2.     Method name      Class name
 *      a. createNewDir --> KOHStringUtil
 *      b. renameFileNameToStr --> KOHFilesUtil
 *      c. deleteFileNow --> KOHFilesUtil
 *
 *  <| ================================================================ |>
 *
 *  2nd Commit - [OSSRH Ready]
 *
 *  1. Updated build.gradle to Publish artifacts to Maven Central Repository via OSSRH
 *  2. MIT LICENSE added
 *  3. Updated to Java 11 version
 *
 *  Note: Unable to use Java 13 due to unresolved errors in compatibility between Gradle and Java 13
 *
 *  <| ================================================================ |>
 *
 *  Init Commit - [Stdlib]
 *
 *  1. Utilities are Error free and Ready-to-use
 *  2. Structure for methods associated with their respective Classes is as follows:
 *
 *      KOHStringUtil
 *          1. userInputString
 *          2. wannaTryAgain
 *          3. generateCurrentTimeStamp
 *          4. doesBeginsWithWhiteSpace
 *          5. doesContainsOnlyWhiteSpace
 *          6. replaceBackSlashWithForwardSlash
 *
 *      KOHFilesUtil
 *          1. writeStrToFile
 *          2. isDirEmpty
 *
 *      MyTimer
 *          1. To find out the Total Time taken Precisely and controlling via following methods:
 *              a. startTimer       b. pauseTimer       c. stopTimer
 *
 *      DirFilesCounter
 *          1. It could be used to find totalSize and count of Files and Dirs
 *
 *  <| ================================================================ |>
 *
 *  Code Developed By,
 *  ~K.O.H..!! ^__^
 */
