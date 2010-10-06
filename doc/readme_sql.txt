1. after install MySQL, start mySql from windown start/programs/


2. create mUser
mysql>grant all privileges on *.* to 'mUser'@'localhost' identified by 'password' with grant option;
mysql>grant all privileges on *.* to 'mUser'@'%' identified by 'password' with grant option;
 
mysql>grant all privileges on *.* to 'myUser'@'localhost' identified by 'myPassword' with grant option;
mysql>grant all privileges on *.* to 'myUser'@'%' identified by 'myPassword' with grant option;


3. access database from DbVisualizer
   3.1 install database driver: from D:\MyProg\DB_Driver\MySql
   3.2 Database URL: jdbc:mysql://localhost:3306/eallhotelmotel

 
4. -- access database from dos
mysql -h localhost -u mUser -p
password is password

3. Useful queries
mysql> SELECT VERSION(), CURRENT_DATE;
mysql> select version(), current_date;
mysql> SELECT SIN(PI()/4), (4+1)*5;
mysql> SELECT
    -> USER()
    -> ,
    -> CURRENT_DATE;

mysql> CREATE DATABASE ehotel;
mysql> USE eallhotelmotel;
mysql> SHOW DATABASES;
mysql> SHOW TABLES;
mysql> DESCRIBE ad_property;

 

4 -- backup data from Linux
4.1 Export A MySQL Database
mysqldump -u mUser -ppassword eallhotelmotel>2009_04_07_file.sql
mysqldump -u myUser -pmyPassword ebooking>2010_09_16_ebooking_dump.sql

4.2 retrive from linux to /cygdrive/d/allhotelmotel/allhotelmotel/database
$ scp root@allhotelmotel.com:/home/mwang/myapp/allhotelmotel/database/2009_04_07_file.sql .

4.3 Import A MySQL Database
mysql -u mUser -ppassword eallhotelmotel < 2009_04_07_file.sql
mysql -u myUser -pmyPassword ebooking < 2010_09_16_ebooking_dump.sql

5. export data
SELECT * INTO OUTFILE 'data.txt'  FIELDS TERMINATED BY '|' FROM ad_test_import;
The file is save to D:\MyProg\MySQL\MySQL Server 5.0\data\eallhotelmotel
   
6. load data
LOAD DATA LOCAL INFILE 'test_export_ad_property.dat' into table ad_test_import FIELDS TERMINATED by '|' IGNORE 1 LINES;

7. copy ad_property table to ad_tmp_property 
INSERT INTO ad_tmp_property SELECT * FROM ad_property; 

8. record in one table but not in another
SELECT ad_ichotel_hotel.recordId 
	FROM ad_ichotel_hotel 
        WHERE NOT EXISTS (SELECT ad_property_company.hotel_code
 			  FROM ad_property_company
 			 WHERE ad_ichotel_hotel.recordId = ad_property_company.hotel_code);
 			 
SELECT ad_property.name 
	FROM ad_property
        WHERE NOT EXISTS (SELECT ad_property_temp.name
 			  FROM ad_property_temp
 			 WHERE ad_property_temp.id = ad_property.id);
 

9. update records in one table based on values in another table.  

    UPDATE supplier 	
    SET supplier_name = 	( SELECT customer.name
    FROM customers
    WHERE customers.customer_id = supplier.supplier_id)
    WHERE EXISTS
      ( SELECT customer.name
        FROM customers
        WHERE customers.customer_id = supplier.supplier_id);


10. -- pagination.
select * from ( 
      select p.*, rownum rnum  
            from (select id, first_name, middle_name, last_name, email, email_status, registration_date, receive_email, last_emailed  from dps_user) p 
       where rownum < 20
) where rnum >=10

select * from ( 
      select p.*, rownum rnum  
            from (select id, first_name, middle_name, last_name, email, email_status, registration_date, receive_email, last_emailed  from dps_user) p 
       where rownum < 102000
) where rnum >=100000

select * from (
   select p.*, rownum rnum
           from (select * from big_table ) p
          where rownum < 100
) where rnum >= 90



LOAD DATA LOCAL INFILE 'state_list.txt' into table state FIELDS TERMINATED by '|';
