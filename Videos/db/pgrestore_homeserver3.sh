#!/bin/sh
BACKUP_FILE="/home/myself/git/videos/Videos/db/dev_videos.backup"
PGPASSWORD="www.myself.com"
psql -h localhost -U videos -d dev_videos -f $BACKUP_FILE
