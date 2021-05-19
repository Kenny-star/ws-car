#!/usr/bin/env bash

mkdir microservices
cd microservices

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=customer-service \
--package-name=com.kenny.microservices.core.customer \
--groupId=com.kenny.microservices.core.customer \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
customer-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=carModel-service \
--package-name=com.kenny.microservices.core.carModel \
--groupId=com.kenny.microservices.core.carModel \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
carModel-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=buildPrice-service \
--package-name=com.kenny.microservices.core.buildPrice \
--groupId=com.kenny.microservices.core.buildPrice \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
buildPrice-service

spring init \
--boot-version=2.3.2.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=car-composite-service \
--package-name=com.kenny.microservices.composite.car \
--groupId=com.kenny.microservices.composite.car \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
car-composite-service

cd ..
