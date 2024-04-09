ECR_REGISTRY="SEU_REGISTRY"
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $ECR_REGISTRY
docker build -t gestaodepontos .
docker tag gestaodepontos:latest $ECR_REGISTRY/gestaodepontos:latest
docker push $ECR_REGISTRY/gestaodepontos:latest
