build-config:
	docker build -t fintech/configserver-builder:latest --cache-from fintech/configserver-builder:latest ./config-server/ -f config-server/Dockerfile-builder

build-disc:
	docker build -t fintech/discoveryserver-builder:latest --cache-from fintech/discoveryserver-builder:latest ./discovery-server/ -f discovery-server/Dockerfile-builder

build-gateway:
	docker build -t fintech/gatewayserver-builder:latest --cache-from fintech/gatewayserver-builder:latest ./gateway-service/ -f gateway-service/Dockerfile-builder

build-acc:
	docker build -t fintech/accountserver-builder:latest --cache-from fintech/accountserver-builder:latest ./account-service/ -f account-service/Dockerfile-builder

build-trans:
	docker build -t fintech/transactionserver-builder:latest --cache-from fintech/transactionserver-builder:latest ./transaction-service/ -f transaction-service/Dockerfile-builder

build-notify:
	docker build -t fintech/notificationserver-builder:latest --cache-from fintech/notificationserver-builder:latest ./notification-service/ -f notification-service/Dockerfile-builder

ddev:
	docker-compose  -f docker-compose.yml up -d --build --force-recreate

