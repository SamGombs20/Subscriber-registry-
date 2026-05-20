## Database Connection Configuration

This project uses **JNDI (Java Naming and Directory Interface)** to manage the 
database connection. Tomcat acts as the connection pool manager and the 
application looks up the datasource at runtime via JNDI.

---

### How it works

1. `context.xml` defines the database resource and connection details
2. `web.xml` declares a reference to that resource
3. `DatabaseConnection.java` performs a JNDI lookup to get a connection

---

### Step 1: Configure context.xml

The file is located at `src/main/webapp/META-INF/context.xml`.
Update it with your MariaDB credentials:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource
        name="jdbc/SubscriberDB"
        auth="Container"
        type="javax.sql.DataSource"
        driverClassName="org.mariadb.jdbc.Driver"
        url="jdbc:mariadb://localhost:3306/mspace"
        username="root"
        password="your_password_here"
        maxTotal="20"
        maxIdle="10"
        maxWaitMillis="10000"/>
</Context>
```

| Field | Description |
|---|---|
| `name` | JNDI name used to look up the datasource in Java code |
| `driverClassName` | MariaDB JDBC driver class |
| `url` | JDBC connection string — change host/port/database if needed |
| `username` | Your MariaDB username |
| `password` | Your MariaDB password |
| `maxTotal` | Maximum number of connections in the pool |
| `maxIdle` | Maximum idle connections kept open |
| `maxWaitMillis` | How long to wait for a connection before timing out |

---

### Step 2: web.xml resource reference

The `web.xml` file at `src/main/webapp/WEB-INF/web.xml` declares 
a reference to the JNDI resource defined in `context.xml`:

```xml
<resource-ref>
    <description>MariaDB Datasource</description>
    <res-ref-name>jdbc/SubscriberDB</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```

The `res-ref-name` must match the `name` attribute in `context.xml` exactly.

---

### Step 3: DatabaseConnection.java JNDI lookup

The application retrieves a connection using JNDI lookup:

```java
public static Connection getConnection() throws Exception {
    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SubscriberDB");
    return ds.getConnection();
}
```

The prefix `java:comp/env/` is required by the Java EE JNDI specification. 
It tells the container to look in the component environment for the resource 
named `jdbc/SubscriberDB`.

---
