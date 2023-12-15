# Nissum Challenge

## Installation

Follow these steps to install and run the project:

1. Clone the repository: `git clone https://github.com/DianaPato/challenge.git`
2. Navigate to the project directory: `cd your-repo`
3. Build the project using Maven:: `mvn clean install`
4. Run the project: `mvn spring-boot:run`
5. Test the API Rest using Postman or another application at `http://localhost:8080`.

## Swagger

http://localhost:8080/swagger-ui/index.html

## DB Creation Scripts

```
    drop table if exists phone cascade 
    drop table if exists user cascade 
    drop sequence phone_seq if exists
    drop sequence user_seq if exists
```

```
    create sequence phone_seq start with 1 increment by 50
    create sequence user_seq start with 1 increment by 50
```

```
    create table phone (
        id bigint not null,
        user_id bigint,
        city_code varchar(255),
        country_code varchar(255),
        number varchar(255),
        primary key (id)
    )
```

```
    create table user (
        is_active boolean,
        created_on timestamp(6),
        id bigint not null,
        last_login timestamp(6),
        last_updated_on timestamp(6),
        email varchar(255),
        name varchar(255),
        password varchar(255),
        token varchar(255),
        primary key (id),
        unique (email)
    )
```

```
    alter table phone 
       add constraint FKb0niws2cd0doybhib6srpb5hh 
       foreign key (user_id) 
       references user
```

## Endpoints

### Registry (creation)

#### POST http://localhost:8080/api/v1/users

```
{
    "name": "Juan Rodriguez",
    "email": "jua23@rodriguez.org",
    "password": "1233",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}
```

#### Expected result

201 (Created)

```
{
    "id": 3,
    "name": "Juan Rodriguez",
    "email": "jua23@rodriguez.org",
"password": "1233",
"phones": [
{
"id": 3,
"number": "1234567",
"cityCode": "1",
"countryCode": "57"
}
],
"isActive": true,
"createdOn": "2023-12-15T01:06:47.023271Z",
"lastUpdatedOn": "2023-12-15T01:06:47.023271Z",
"lastLogin": "2023-12-15T01:06:47.023271Z",
"token": "
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTcwMjYxMzIwNiwiZXhwIjoxNzAyNjE0NjQ2fQ.aeuVTyYrwe9tYZl2CFpoUeQ5LLNZq4rIFvU9jpN9mUo"
}
```

#### Error 400 (Bad Request)

```
{
"mensaje": "name: must not be blank. "
}
```

### GET Users

#### GET http://localhost:8080/api/v1/users

#### Expected Result

```
[
    {
        "id": 1,
        "name": "Juan Rodriguez",
        "email": "jua2@rodriguez.org",
        "password": "1233",
        "phones": [
            {
            "id": 1,
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
            }
        ],
        "isActive": true,
        "createdOn": "2023-12-15T00:54:00.372110Z",
        "lastUpdatedOn": "2023-12-15T00:54:00.372110Z",
        "lastLogin": "2023-12-15T00:54:00.372110Z",
        "token": "
        eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTcwMjYxMjQ0MCwiZXhwIjoxNzAyNjEzODgwfQ.gOvIqoKk6jP5B--LQENrx4WAwsqo0eLZREhu1WgOnsg"
    },
    {
        "id": 3,
        "name": "Juan Rodriguez",
        "email": "jua23@rodriguez.org",
        "password": "1233",
        "phones": [
            {
            "id": 3,
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
            }
            ],
        "isActive": true,
        "createdOn": "2023-12-15T01:06:47.023271Z",
        "lastUpdatedOn": "2023-12-15T01:06:47.023271Z",
        "lastLogin": "2023-12-15T01:06:47.023271Z",
        "token": "
        eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTcwMjYxMzIwNiwiZXhwIjoxNzAyNjE0NjQ2fQ.aeuVTyYrwe9tYZl2CFpoUeQ5LLNZq4rIFvU9jpN9mUo"
    }
]
```

### Delete

#### DELETE http://localhost:8080/api/v1/users/2

#### Expected response 204 (No content)

### Update User

#### PUT http://localhost:8080/api/v1/users

```
{
    "id": 1,
    "name": "New Name",
    "email": "jua23454@rodriguez.org",
    "password": "1233",
    "phones": [
        {
        "number": "1234567",
        "citycode": "1",
        "contrycode": "57"
        }
    ]
}
```

#### Expected Response: 200 (OK)

```

{
    "id": 1,
    "name": "New Name",
    "email": "jua23454@rodriguez.org",
    "password": "1233",
    "phones": [
        {
        "id": 5,
        "number": "1234567",
        "cityCode": "1",
        "countryCode": "57"
        }
    ],
    "isActive": true,
    "createdOn": "2023-12-15T00:54:00.372110Z",
    "lastUpdatedOn": "2023-12-15T01:20:17.576602Z",
    "lastLogin": "2023-12-15T01:20:17.576602Z",
    "token": "
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTcwMjYxMjQ0MCwiZXhwIjoxNzAyNjEzODgwfQ.gOvIqoKk6jP5B--LQENrx4WAwsqo0eLZREhu1WgOnsg"
}
```

