public class SQL_Note_4 {
}
/**

 --17Aug2021

 CREATE TABLE employees
 (
 employee_id number(9),
 employee_first_name varchar2(20),
 employee_last_name varchar2(20)
 );

 INSERT INTO employees VALUES(14, 'Chris', 'Tae');
 INSERT INTO employees VALUES(11, 'John', 'Walker');
 INSERT INTO employees VALUES(12, 'Amy', 'Star');
 INSERT INTO employees VALUES(13, 'Brad', 'Pitt');
 INSERT INTO employees VALUES(15, 'Chris', 'Way');

 SELECT * FROM employees;

 CREATE TABLE addresses
 (
 employee_id number(9),
 street varchar2(20),
 city varchar2(20),
 state char(2),
 zipcode char(5)
 );

 INSERT INTO addresses VALUES(11, '32nd Star 1234', 'Miami', 'FL', '33018');
 INSERT INTO addresses VALUES(12, '23rd Rain 567', 'Jacksonville', 'FL', '32256');
 INSERT INTO addresses VALUES(13, '5th Snow 765', 'Hialeah', 'VA', '20121');
 INSERT INTO addresses VALUES(14, '3rd Man 12', 'Weston', 'MI', '12345');
 INSERT INTO addresses VALUES(15, '11th Chris 12', 'St. Johns', 'FL', '32259');

 SELECT * FROM addresses;


 --AlIASES-------------------------------------------------

 --1)Select employee first name and state, for emplyee first name use “firstname” as field name and for state use “employee state” as field name
 SELECT e.employee_first_name, a.state
 FROM employees e, addresses a
 WHERE e.employee_id = a.employee_id;

 --How to put multiple fields into a single field and use aliase for the field
 --2)Get employee id use "id" as field name, get firstname and lastname put them into the same field and use "full_name" as field name
 SELECT employee_id AS id, employee_first_name ||' '|| employee_last_name AS full_name
 FROM employees;


 --GROUP BY----------------------------------------------------

 CREATE TABLE workers
 (
 id number(9),
 name varchar2(50),
 state varchar2(50),
 salary number(20),
 company varchar2(20)
 );

 INSERT INTO workers VALUES(123456789, 'John Walker', 'Florida', 2500, 'IBM');
 INSERT INTO workers VALUES(234567890, 'Brad Pitt', 'Florida', 1500, 'APPLE');
 INSERT INTO workers VALUES(345678901, 'Eddie Murphy', 'Texas', 3000, 'IBM');
 INSERT INTO workers VALUES(456789012, 'Eddie Murphy', 'Virginia', 1000, 'GOOGLE');
 INSERT INTO workers VALUES(567890123, 'Eddie Murphy', 'Texas', 7000, 'MICROSOFT');
 INSERT INTO workers VALUES(456789012, 'Brad Pitt', 'Texas', 1500, 'GOOGLE');
 INSERT INTO workers VALUES(123456710, 'Mark Stone', 'Pennsylvania', 2500, 'IBM');

 SELECT * FROM workers;


 -- find the total salary for every employee
 SELECT name, SUM(salary) AS total_salary
 FROM workers
 GROUP BY name
 ORDER BY total_salary DESC; --1)order by is used after group by 2)Use aliase as field name

 --Find the number of employees per state
 SELECT state, COUNT(state) AS number_of_employees
 FROM workers
 GROUP BY state
 ORDER BY number_of_employees DESC;

 --Find the number of the employees whose salary is more than $2000 per company
 SELECT company, COUNT(*) AS "number of employees" --If column name is not matter to count, you can use *
 FROM workers
 where salary>2000
 GROUP BY company;

 --Find the minimum and maximum salary for every company
 SELECT company, MAX(salary) AS "Max Salary" , MIN(salary) AS "Min Salary"
 FROM WORKERS
 GROUP BY company;


 --HAVING CLAUSE---------------------------------------------

 --Find the total salary if it is greater than 2500 for every employee
 SELECT name, SUM(salary) AS "Total Salary"
 FROM workers
 GROUP BY name
 HAVING SUM(salary) >2500;

 --Note: If you uncomment the following code it will give error,
 --because we cannot use “aggregate functions”(SUM(), MAX(), MIN(), COUNT(), AVG()) after WHERE Clause, like this:

 --SELECT name, SUM(salary) AS "Total Salary"
 --FROM workers
 --WHERE SUM(salary) >2500 ==> After WHERE, we cannot use "aggregate functions" (SUM(),MAX(),MIN(), AVG())
 --GROUP BY name

 --Find the number of employees if it is more than 1 per state
 SELECT state, COUNT(state) AS "number of employees"
 FROM workers
 GROUP BY state
 HAVING COUNT(state)>1; --1)HAVING is for filtering for GROUP BY 2)After HAVING,you have to use aggregate functions, do not usefield names.

 --Find the minimum salary if it is more than 2000 for every company
 SELECT company, MIN(salary) AS "Minimum Salary"
 FROM workers
 GROUP BY company
 HAVING MIN(salary)>salary

 --Find the maximum salary if it is less than 3000 for every state
 SELECT state, MAX(salary) AS maximum_salary
 FROM workers
 GROUP BY state
 HAVING MAX(salary)<3000;

 --UNION Operator------------------------------------------------------------
 --1)It is used to join the result of 2 queries.
 --2)UNION Operator returns unique records everytime, if there are repeated records it removes the repeated ones.
 --3)By using UNION Operator we are being able to put different fields into a single field like in the following example.
 --4)When you put different fields into a single field by using UNION Operator, field must have same data type in same size.

 --Find the state whose salary is greater than 3000, employee names less than 2000 without duplication.
 SELECT state AS "State or Employee Name", salary
 FROM workers
 WHERE salary > 3000
 UNION
 SELECT name AS "State or Employee Name", salary
 FROM workers
 WHERE salary < 2000;


 --INTERSECT Operator-------------------------------------------
 --INTERSECT Operator: Returns common  records of 2 queries. 2) It returns unique data.
 --Find all common employee names whose salary is greater than 1000, less than 2000
 select name, salary
 from workers
 where salary >1000

 intersect

 select name, salary
 from workers
 where salary <2000


 --Find all common employee names whose salary is greater than 2000 and company name is IBM, APPLE or GOOGLE
 select name
 from workers
 where salary >2000

 intersect

 select name
 from workers
 where company IN('IBM', 'APPLE', 'MICROSOFT');


 --MINUS Operator---------------------------------------------
 --Find the employee names whose salary is less than 3000 and not working in GOOGLE
 select name
 from workers
 where salary  < 3000

 MINUS

 select name
 from workers
 where company = 'GOOGLE';



 --JOINS---------------------------------
 --1)INNER JOIN: Returns common data
 --2)LEFT JOIN: Returns all data from first table
 --3)RIGHT JOIN: Returns all data from seconds table
 --4)FULL JOIN: Returns all data from both table
 --5)SELF JOIN: You will have a single table but you will use it as 2 tables.

 CREATE TABLE my_companies
 (
 company_id number(9),
 company_name varchar2(20)
 );
 INSERT INTO my_companies VALUES(100, 'IBM');
 INSERT INTO my_companies VALUES(101, 'GOOGLE');
 INSERT INTO my_companies VALUES(102, 'MICROSOFT');
 INSERT INTO my_companies VALUES(103, 'APPLE');

 SELECT * FROM my_companies;


 CREATE TABLE orders
 (
 order_id number(9),
 company_id number(9),
 order_date date
 );
 INSERT INTO orders VALUES(11, 101, '17-Apr-2020');
 INSERT INTO orders VALUES(22, 102, '18-Apr-2020');
 INSERT INTO orders VALUES(33, 103, '19-Apr-2020');
 INSERT INTO orders VALUES(44, 104, '20-Apr-2020');
 INSERT INTO orders VALUES(55, 105, '21-Apr-2020');

 SELECT * FROM orders;


 --select company name, order id and order date for common companies.
 SELECT mc.company_name, o.order_id, o.order_date
 FROM my_companies mc INNER JOIN orders o
 ON o.company_id = mc.company_id;


*/