# Data collection service
Application is designed to collect data from users.

## Technologies ##
1. IDE: IntelliJ IDEA 
2. Java 8
3. Hibernate 5.2.5.Final
4. Play Framework 2.5.x (scala-templating) 
5. Database: PostgreSQL 9.4
6. WebSockets
7. Bootstrap, HTML, CSS, JavaScript/JQuery 

## How to use ##
Default admins:
 1. Artem   qwerty
 2. Alex    12345
 3. Admin   admin

For unauthorized users
1. Responses collecting page. It gives possibility to submit response after filling in all active fields created by admin on page domainname.com/createfield. Optional fields can be left empty. Resetting form will clean out all entered data.

For authorized users

1. Create/edit field page. Here admin is able to create new fields with definite number of properties. All properties are required. After saving new field user will be redirected to page domainname.com/fields. Page is available on path domainname.com/createfield.

2. List of fields page. Page is available by direct link. Here all Fields stored in database will be listed. Admin is able to create, edit and delete fields. Removing fields is done with AJAX. Page is available on path domainname.com/fields

3. Responses page. All the responses completed by users are collected here. They appear in this list automatically after submitting. Columns on this page correspond to each field created on page domainname.com/createfield. Page is available on path domainname.com/responses.

4. On page domainname.com/admin you can add new admin into DB.

## Development ##
IntelliJ IDEA
Open build.sbt file in your IDE using File -> Open.

## Not implemented ##
1. Slider.


