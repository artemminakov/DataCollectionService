# --- Sample dataset

# --- !Ups

INSERT INTO admin VALUES('Artem', 'qwerty');
INSERT INTO admin VALUES('Alex', '12345');
INSERT INTO admin VALUES('Admin', 'admin');

INSERT INTO field(fieldId, label, type, isRequired, isActive)
  VALUES(1,'Name', 'SINGLELINETEXT', TRUE, TRUE );
INSERT INTO field(fieldId, label, type, isRequired, isActive)
  VALUES(2,'Email', 'SINGLELINETEXT', TRUE, TRUE);
INSERT INTO field(fieldId, label, type, isRequired, isActive, options)
  VALUES(3,'Sex', 'RADIOBUTTON', FALSE, TRUE, 'Male
                                              Female');
INSERT INTO field(fieldId, label, type, isRequired, isActive)
  VALUES(4,'Date of birth', 'DATE', FALSE, TRUE);
INSERT INTO field(fieldId, label, type, isRequired, isActive, options)
  VALUES(5,'Sample combobox', 'COMBOBOX', FALSE, TRUE, 'Option 1
                                                       Option 2
                                                       Option 3');
INSERT INTO field(fieldId, label, type, isRequired, isActive)
  VALUES(6,'Comment', 'MULTILINETEXT', FALSE, TRUE);

INSERT INTO response(responseId) VALUES (1);
INSERT INTO response(responseId) VALUES (2);

INSERT INTO responsecontent(responseContentId, content, field_fieldid)
    VALUES(1, 'Artem', 1);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(2, 'artem@gmail.com', 2);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(3, 'Male', 3);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(4, '30 May 1995', 4);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(5, 'Option 2', 5);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(6, 'Alex', 1);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(7, 'alex@gmail.com', 2);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(8, 'Male', 3);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(9, '30 May 1996', 4);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(10, 'Option 3', 5);
INSERT INTO responsecontent(responseContentId, content, field_fieldid)
  VALUES(11, 'Very good', 6);

INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(1, 1);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(1, 2);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(1, 3);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(1, 4);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(1, 5);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(2, 6);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(2, 7);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(2, 8);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(2, 9);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(2, 10);
INSERT INTO response_responsecontent(response_responseid, responsecontents_responsecontentid)
  VALUES(2, 11);

# --- !Downs

DELETE FROM admin;
DELETE FROM response_responsecontent;
DELETE FROM responsecontent;
DELETE FROM response;
DELETE FROM field;