# Kafka Task – Order & Payment Microservices

A demo project showcasing **event-driven communication** with two Spring Boot microservices and **Apache Kafka**.

- **Order-Service** → REST API + Kafka producer  
- **Payment-Service** → Kafka consumer  
- **Kafka** → Event streaming backbone  
- **Docker Compose** → Local setup  
- **AWS EC2** → Cloud deployment

## Quick Test
```bash
curl -X POST http://<host>:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":"test-user","amount":100.50}'
