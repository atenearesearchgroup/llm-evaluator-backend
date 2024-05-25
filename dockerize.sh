DOCKER_BUILDKIT=1 docker build -t hermesanalyzer/backend .
docker network create hermesanalyzer-backend-network
docker network connect hermesanalyzer-backend-network hermesanalyzer-backend-mysql-1
docker container rm hermesanalyzer-backend-app
docker run -d -p 8080:8080 --network hermesanalyzer-backend-network --name hermesanalyzer-backend-app -t hermesanalyzer/backend $*