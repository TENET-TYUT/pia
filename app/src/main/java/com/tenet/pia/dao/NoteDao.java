        }
        while (cursor.moveToNext()) {
            String notetitle = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
            String notecontent = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
            String createtime = cursor.getString(cursor.getColumnIndex(CREATE_TIME));
            Note note = new Note(notetitle, notecontent,createtime);
            noteList.add(note);
        }

        db.close();
        return noteList;
    }


}
