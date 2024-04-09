@REM TODO verificar esse arquivo preenchendo as coisas abaixo

aws ecr get-login-password --region us-east-1 --profile [SEU_PROFILE] | docker login --username AWS --password-stdin [SEU_ECR]
docker build -t gestaodepontos .
docker tag gestaodepontos:latest [SEU_ECR]/gestaodepontos:latest
docker push [SEU_ECR]/gestaodepontos:latest