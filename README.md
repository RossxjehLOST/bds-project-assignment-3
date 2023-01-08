# BDS Project Assignment 3


## Description
This is a repository for bds project assignment 3 from subject BPC-BDS. Goal of this project was to create desktop application with GUI that uses the database from previous assignment. The application must have proper sign-in window with username/password authentication (no fake login). The application must be able to perform CRUDE operations on at least one table. User must be able to read, edit, create or delete entries in database. It also must have a filter option. Neccessary is also the backup script, that backs up the database every midnight.

## State of project 
As far as I can tell, the project is finished. I was inspired by Pavel Šeda's template, but I tried my best to leave my footprint on this project. It may not be perfect, it may not be pleasant to look at, but it WORKS and I hope it gets me through this subject. I am not the best programmer (if I can even call myself that), so please don't be harsh with the rating.

## Contents of this repository
- **README.md** you are reading it now
- **backup Folder** this folder contains the backup script
- **src/main Folder** source code of this project
- **.gitignore** added so that git ignores any unnecessary files like the target folder or others(e.g., IDE configs)
- **Documentation.pdf** documentation about my project in .pdf form so it can be opened in browser
- **LICENSE** suitable license (MIT)
- **pom.xml** contains all dependencies used in my project

## How to build and run the project
Enter the following command in the project root directory to build the project.

```shell
$ mvn clean install
```

To run the project:

```shell
$ java -jar .\target\pc-shop-1.0.0.jar
```

When you get past this, a log-in prompt appears. 
For email use `Flowers.Marian.507@gmail.com` and password is `user`

## Author
- [REDACTED]
