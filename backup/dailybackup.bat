Schtasks /create /SC daily /TN dailybackup /TR C:\Program Files\PostgreSQL\14\bin\backup.bat /ST 00:00:00 /ED 01/01/2023 /RL HIGHEST