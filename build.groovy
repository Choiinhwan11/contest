plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.2'
    id 'io.spring.dependency-management' version '1.1.6'
}

group = 'org.example'
version = '0.0.1-SNAPSHOT'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType(JavaCompile) {
    options.compilerArgs += ['-parameters']
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
    maven {
        url "https://repo.spring.io/milestone"
    }
    maven {
        url "https://repo.spring.io/release"
    }
    mavenLocal()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-websocket'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-hateoas'
    implementation 'jakarta.validation:jakarta.validation-api:3.0.2'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'mysql:mysql-connector-java'
    implementation 'org.springframework.session:spring-session-data-redis'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.kafka:spring-kafka-test'
    implementation 'org.apache.poi:poi:5.2.3'
    implementation 'org.apache.poi:poi-ooxml:5.2.3'
    implementation 'org.locationtech.proj4j:proj4j:1.2.1'
    implementation 'org.apache.httpcomponents.client5:httpclient5:5.1'
    implementation 'org.apache.httpcomponents.core5:httpcore5:5.1'
    implementation 'org.json:json:20210307'
    implementation 'io.lettuce.core:lettuce-core:6.2.3.RELEASE'
}

tasks.named('test') {
    useJUnitPlatform()
}