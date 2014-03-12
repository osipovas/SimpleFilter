SimpleFilter
============

The first application assignment for ECE 493 (Winter 2014).

ECE 493 Software Systems Design Project
Winter 2013
Laboratory Assignment #1

Due Date: 2pm Monday, Feb. 4, 2013

Objective:  Implement two simple noise reduction filters (the mean filter and the median 
filter) on the Galaxy Nexus emulator. 

Requirements:  Write  an  Android  application  that  loads  an  image,  and  allows  you  to 
perform noise reduction on that image.

1.  The  screen  should  initially  display  a  blank  image,  and  provide  a  menu  item  for 
selecting  an  image  to  load.  This  should  take  the  user  to  a  screen  with  a  directory 
listing of available image files. When the user selects a file to load, the app returns to 
the first screen and the image is displayed.

2.  Once  the  image  is  loaded,  the  user  should  have  the  option  of  performing  noise 
reduction by a mean filter (square convolution  mask), or  by a median filter (again, a 
square  convolution  mask).  The  size  of  the  convolution  mask  should  be  an  odd 
positive  integer  of  the  user’s  choice  (though  obviously  it  must  be  smaller  than  the 
image).

3.  When a noise-reduced image has  been computed, it should be displayed to the user. 
At  that  point,  the  previous  image  should  be  discarded  (do  not  delete  the  file,  but 
remove the image from memory).

4.  The  user  should  be  able  to  load  a  new  image  at  any  time.  Only  the  current  image 
needs to be kept in memory; once a new image is loaded, the previous image should 
again be discarded.

Submission:  Email  your  source  code  to  the  TA,  with  a  copy  to  Dr.  Dick,  before  the 
deadline on the due date. You will be required to demo your program for the TA within 
one week of the due date. All demos will take place in the E5-005 laboratory.

Grading
Application compiles:      25%
Correct operation (emulator):   75%
