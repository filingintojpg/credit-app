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
