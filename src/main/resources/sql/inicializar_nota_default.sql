1 es el ID de la nota por default

INSERT INTO D2D_NOTE_OCCUPATION (NOTE_iD, OCCUPATION_ID)
SELECT 1, D2D_OCCUPATION.ID  FROM D2D_OCCUPATION

INSERT INTO D2D_NOTE_SPECIALTY (NOTE_ID, SPECIALTY_ID)
SELECT 1, D2D_SPECIALTY.ID  FROM D2D_SPECIALTY