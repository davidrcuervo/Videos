#!/bin/sh
BACKUP_FILE="/home/myself/git/videos/Videos/db/dev_videos.backup"
PGPASSWORD="www.myself.com"
pg_dump -h localhost -p 5432 -U videos -C -v -f $BACKUP_FILE dev_videos
