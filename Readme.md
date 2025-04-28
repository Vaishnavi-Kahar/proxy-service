## API Contract:

#### Call Service API
```
API: /api/v1/call-service
METHOD: GET
HEADERS: 
    X-JWT-TOKEN : <jwt token>
    Content-Type : application/json
REQUEST BODY:
    {
        "service": <service_name>, // String
        "url": <url>, // String
        "pathParams": {}, // Map<String,String>
        "queryParams":{} // Map<String,String>
    }
```
#### Signup
```
API: /home/v1/signup
METHOD: POST
REQUEST BODY:
    {
        "name": <name>, //String
        "email": <email>, // String
        "password": <password>, // String
    }
```

#### Login
```
API: /home/v1/login
METHOD: POST
REQUEST BODY:
    {
        "email": <email>, // String
        "password": <password>, // String
    }
```

#### Generate token
```
API: /token/v1/generate
METHOD: POST
REQUEST BODY:
    {
        "email": <email>, // String
        "service": <service>, // String
    }
```

#### Get Available Services
```
API: /home/v1/services
METHOD: GET
```