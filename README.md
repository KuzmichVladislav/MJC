# MJC

REST API **"Gift certificates"**. The **User** places an **Order** and pays for the purchase of **Gift certificates**. 
The **Certificate** has a set of **Tags** for easy searching. The **User** can **Register** and **log in** under their name, 
place an **Order**, as well as edit their data. The **Administrator** can add new **Certificates**, **Tags**, edit and 
delete them. An **Unregistered user** can see all **Certificates** or a detailed description of each.

**User action:**

- Admin: 
    - Create new Gift Certificate
    - Create new Tag
    - Deleting / restoring Gift Certificate
    - View all Users
    - Deleting / restoring Users
    - View all Orders
    - Editing information about yourself
- Client:
    - Create order
    - View all Gift Certificates
    - Search Gift Certificates by parameters
    - Viewing information about Gift Certificate
    - Editing information about yourself
- Guest:
    - View all Gift Certificates
    - Viewing information about Gift Certificate
    - Sign Up
    - Log In

**Technologies and libraries used**

- Java 11
- Spring Framework
- Spring MVC
- Spring Boot
- Spring Data
- Spring Security
- Hibernate
- JUnit
- Mockito
- MySQL
- Gradle

**Database visualization**
![database](https://i.ibb.co/BPdvV8H/gifts.png)
