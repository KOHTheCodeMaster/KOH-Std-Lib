package dev.koh.stdlib.app;

import dev.koh.stdlib.utils.KOHStringUtil;

public class A {

    public static void main(String[] args) {
        System.out.println(KOHStringUtil.generateCurrentTimeStamp());
    }

}

/*
 *  Date Created  : 7th November 2K19, 12:00 PM..!!
 *  Last Modified : 7th November 2K19, 04:23 PM..!!
 *
 *  <| ================================================================ |>
 *
 *  Project Title: KOH-Std-Lib
 *
 *  Description:
 *
 *  This project is a Java-Library with Utilities to ease up the tasks.
 *
 *  <| ================================================================ |>
 *
 *  Latest Update:
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
