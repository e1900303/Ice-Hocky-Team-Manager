# Ice-Hocky-Team-Manager
*An app to manage your hockey team!*

### Project Description:

The project is an application which is used to store data of the matches the team has participate. 
User can enter the date, opponent team’s name and the result of that match. Thus, user can enter 
the point each team member has scored during the game. The list of players (which is stored in a 
combo box in the GUI) is read from the file *“players.txt”*. User can delete, add new player by editing
the text file as he/she want. All the matches user has created can be seen by choosing the match 
date in the combo box in GUI. And the statistics of players, which is basically the number of point 
they have scored through out all the matches, can also be seen by simply clicking the button to 
show.

![screenshot1](https://github.com/e1900303/Ice-Hocky-Team-Manager/blob/master/oop1.PNG?raw=true)

*Screenshot #1: The general look of the application*

![screenshot2](https://github.com/e1900303/Ice-Hocky-Team-Manager/blob/master/oop2.PNG?raw=true)

*Screenshot #2: The player list in combo box*

![screenshot3](https://github.com/e1900303/Ice-Hocky-Team-Manager/blob/master/oop3.PNG?raw=true)

*Screenshot #3: The date list in combo box which is used to see created matches*

![screenshot4](https://github.com/e1900303/Ice-Hocky-Team-Manager/blob/master/oop4.PNG?raw=true)

*Screenshot #4: Stats of players shown in output screen*

![screenshot5](https://github.com/e1900303/Ice-Hocky-Team-Manager/blob/master/oop5.PNG?raw=true)

*Screenshot #5: File being saved*

![screenshot6](https://github.com/e1900303/Ice-Hocky-Team-Manager/blob/master/oop6.PNG?raw=true)

*Screenshot #6: Menu bar*

### The source code file includes 4 classes:

-**Main.class**: this is the main class which is used to execute the program. It contains all the graphical 
user interface, and methods required to make the program runnable: creating games, saving to files, 
opening files, showing stats of players, etc
-**Date.class**: this class is used to parse date strings into integer variables.
**Match.class**: this is the class which is used to output the match data and the score team members
made during the match.
-**Player.class**: this class is used to output the summed point of each player through out all the 
matches.
-**players.txt**: this is the text file with a list of all the team members. The program implements the list 
to the combo box in the GUI. User can edit the file as he/she wants: deleting, adding new members.




