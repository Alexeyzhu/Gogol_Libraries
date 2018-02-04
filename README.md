# Gogol_Libraries

Installing manual ( All setting have any sence only for Intellij IDEA Pro version )

First, clone repositiry wherever in computer and open project by Intellij.

Second, intall JDBS module. Open  File -> Project Structure -> SDKs . Push plus button and choose file 'sqlite-jdbc-3.21.0' in folder 'sources' in the project folder.

Third, add databaces to Intellij. Open Database layout and click plus button in order to connect to databases. Choose 'SQLite'.In opened window show to program a path to the databases by clicking to the plus button near the 'File' field. Databases are in the 'sources' folder in the project folder. File's name - 'gogollib.db'. 

Fourth, copy the URL of the databases ( field under the 'File' field in 'Data Source Properties' window ). Open 'DBconnection' class and paste your own URL in 'connectionURL' variable.


