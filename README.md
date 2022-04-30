# CS-410-Databases_Final
# Authors : Zarek Sliwoski, Simon Arca Costas

# Summary
This repo contains the code for the CS-410 Final Project

A Shell application written for managing grades in a wide number of classes.
It's written in Java for processing commands and makes use of MySQL for the access and storage of data.

# Implementation
To implement our shell terminal, we seperate all input by spaces and process it according to 
first command, i.e. "add-student" then we count the number of additional strings we have.
Based on the number of strings from there, we can determine what query we need to execute.

If input is of the wrong type, or there is too much of it, the command will be rejected with
a MySQL exception or an error from the function.

# Thoughts
There was a lot that was limiting about our implementation, we made an effort to slim down the
amount of data that needed to be stored significantly. I think the need for so many commands
put us under a bit of a time crunch right at the end of the semester.