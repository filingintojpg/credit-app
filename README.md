## Run instruction

### Required
- Docker and Docker Compose

### Quick start
```bash
cp .env.example .env && docker-compose up -d --build
```

### Run application (Services + Database)
1. Copy `.env.example` to `.env` and fill in the required values
2. Build project: `docker-compose build`
3. Run project: `docker-compose up -d`

### Check status
- List containers: `docker-compose ps`
- View logs: `docker-compose logs -f`
- Check specific service: `docker-compose logs application-service`

### Stop services
- Stop all: `docker-compose down`
- Stop and remove volumes: `docker-compose down -v`

## API-endpoints

### GET endpoints
- ```/application/{applicationId}/status``` - Get status of application

### POST
- ```/application/create``` - Create new application
```bash
{
  "lastName": "Иванов",
  "firstName": "Иван",
  "middleName": "Иванович",
  "passportSeries": "1234",
  "passportNumber": "567890",
  "maritalStatus": "SINGLE",
  "address": "Москва, ул. Ленина, 1",
  "phone": "89965449157",
  "organization": "ООО Ромашка",
  "position": "Менеджер",
  "employedAt": "2020-01-15",
  "amount": 20000,
  "term": 10,
  "birthDate": "1990-05-20"
}
```

- ```/application``` - Get applications with filters and pagination
```bash
{
    "filter": {
        "statuses": ["APPROVED"],
        "amount": 20000,
        "term": 10,
        "phone": "89965449157"
    },
    "pagination": {
        "page": 2,
        "size": 5
    }
}
```
