set PGPASSWORD=admin
"C:\Program Files\PostgreSQL\14\bin\pg_dump.exe" -h "localhost" -p"5433" -U "postgres" -no-password -verbose -format=c -blobs -section=pre-data -section=data -section=post-data "Projekt" > C:\Users\rosik\Desktop\School\Projects\backups_db
