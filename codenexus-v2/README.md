# .JAR da CodeNexus

---

## Qual o objetivo desse projeto?

- Extrair arquivos .xlsx de um bucket S3 da AWS
- Ler e processar os dados dos arquivos extraídos
- Inserir esses dados numa database MySQL

---

## Ferramentas Utilizadas

### 1. SDK AWS

SDK, ou Software Development Kit, trata-se de um conjunto de ferramentas, bibliotecas, 
documentação e exemplos de código fornecidos por uma empresa ou comunidade para ajudar 
desenvolvedores a criar aplicações para uma plataforma específica.

No nosso caso iremos utilizar o SDK da AWS para realizar a comunicação com o bucket S3,
usando dos recursos de listagens de objetos de um bucket e extração desses objetos.

### 2. Apache POI

Antes de saber o que é o Apache POI é importante entender o que significa esse termo "Apache".

A Apache Software Foundation é uma organização sem fins lucrativos que mantém mais de 300 projetos
open source (código livre) licenciados para que os desenvolvedores os utilizem livremente.

Um exemplo é o próprio Apache POI, desenvolvida pela Apache Foundation que permite o código java
ler, criar, editar e extrair dados de documentos do Microsoft Office: 

- Excel (.xls, .xlsx) → manipulação de planilhas.
- Word (.doc, .docx) → leitura e escrita de textos.
- PowerPoint (.ppt, .pptx) → criação e edição de apresentações.

### 3. JDBC

O JDBC (Java Database Connectivity) é uma API da linguagem Java que permite que aplicações Java 
se conectem e interajam com bancos de dados relacionais. Funciona como uma ponte entre o código 
Java e o banco de dados, usando drivers específicos para cada sistema de banco de dados 
(como MySQL, PostgreSQL, Oracle, SQL Server, etc.).

---

## Ordem de Leitura

Para fins de entendimento, recomendamos as classes do projeto sejam lidas em uma ordem específica.

1. <u>S3Provider:</u> Responsável por criar o cliente S3 e fornecer as credenciais necessárias
para que ele se comunique com o bucket na AWS.
2. <u>S3Service:</u> Responsável por 

---

## Código com Comentários

### 1. S3Provider
```java
package school.sptech.client;

// Importe de recursos do SDK da AWS
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

// Classe utilizada para criar o cliente que interage com o S3
public class S3Provider {

    // Provedor de credenciais usado pelo SDK
    private final AwsCredentialsProvider credentials;

    //Construtor da classe.
    public S3Provider() {
        // DefaultCredentialsProvider: Aplica automaticamente a cadeia de credenciais da AWS.
        this.credentials = DefaultCredentialsProvider.create();
    }

    // Constrói e retorna uma instância do cliente S3 (S3Client) seguindo uma série de etapas
    public S3Client getS3Client() {
        return S3Client.builder() // Inicia a construção do cliente
                .region(Region.US_EAST_1) // Define a região da AWS
                .credentialsProvider(credentials) // Configura o provedor de credenciais
                .build(); // Finaliza a construção e retorna o S3Client
    }
}
```

- **getS3Client()**: Cria e retorna um objeto S3Client, responsável por realizar a 
comunicação com o bucket S3 através de seus métodos.

### 2. S3Service
```java
package school.sptech.service;

// Importe de recursos do SDK da AWS
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

// Importe de recursos do próprio Java
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class S3Service {
  // Atributos
  private final S3Client s3Client; // Cliente S3, imutável após instanciar o objeto ExtractService
  private String bucketName; // Nome do bucket

  // Construtor
  public S3Service (S3Client s3Client) {
    this.s3Client = s3Client;
  }

  // Retorna uma Lista de Buckets da sua AWS
  public List<Bucket> listarBuckets () {
    // listBuckets() retorna um objeto ListObjectsResponse
    // buckets() extrai do objeto ListObjectsResponse somente o nome dos buckets
    // Obs: não recebe parâmetros, pois o S3Client já possui tudo que precisa (suas credenciais)
    return s3Client.listBuckets().buckets();
  }

  // Retorna uma Lista dos objetos de um bucket específico
  public List<S3Object> listarObjetos (String nomeBucket) {
    // Requisição usada pelo S3Client para listar os objetos de um bucket
    ListObjectsRequest listObjects = ListObjectsRequest.builder() // Inicia a construção da instância ListObjectsRequest
            .bucket(nomeBucket) // Define o nome do bucket que será listado
            .build();

    // listObjects() recebe a instância ListObjectsRequest como parâmetro que define o bucket
    // listObjects() retorna um objeto ListObjectsResponse
    // contents() extrai do objeto ListObjectsResponse apenas a lista de objetos do bucket
    return s3Client.listObjects(listObjects).contents();
  }

  // Extrai os objetos da lista de objetos, passada como parâmetro, de um bucket específico e salva localmente
  // Exceções IOException são tratados na Main
  public void extrairObjetos (List<S3Object> objects) throws IOException {
    for (S3Object object : objects) { // Percorre a lista de objetos
      // Instanciamento do objeto que possui o nome do bucket e nome do objeto atual
      // Usado como parâmetro do getObject()
      GetObjectRequest getObjects = GetObjectRequest.builder() // Inicia a construção da instância GetObjectRequest
              .bucket(bucketName) // Define o nome do bucket que será listado
              .key(object.key()) // Define o nome único do objeto (atual da lista)
              .build(); // Finaliza a construção e retorna o objeto

      // Vamos destrinchar essa linha de código
      InputStream inputStream = s3Client.getObject(getObjects, ResponseTransformer.toInputStream());
      // InputStream: Recebe o objeto (arquivo) baixado como um fluxo de dados (stream), padrão de consumo do Java
      // getObject(): Baixa um objeto do Bucket, recebe dois parâmetros
      // - Parâmetro 1: Instância da classe GetObjectRequest que contém o nome do bucket e nome do objeto a ser baixado
      // - Parâmetro 2: O ResponseTransformer define como o conteúdo do objeto vai ser consumido.
      //                No nosso caso, como um fluxo de dados (inputStream)

      // Objeto que serve como referencia a arquivo que ainda não existe
      File file = new File("ArquivosS3/" + object.key());
      Files.copy(inputStream, file.toPath());
      // Files.copy: Consome um fluxo de dados e salva em forma de um arquivo local
      // - Parâmetro 1: Fluxo de dados a ser consumido
      // - Parâmetro 2: Usa o caminho do arquivo referência para criar um arquivo real
      //                com o mesmo nome do objeto S3
      System.out.println("Arquivo baixado: " + object.key());
    }
  }

  // Getters e Setters
  public S3Client getS3Client() {
    return s3Client;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }
}
```

- **listarBuckets()**: Cria e retorna uma lista de Buckets S3 a partir das credenciais fornecidas ao S3Client

- **listarObjetos()**: Cria e retorna uma lista de objetos de um bucket S3 específico. Recebe o nome do bucket
como parâmetro e usa para construir uma instância ListObjectsRequest que é usada pelo S3Client

- **extrairObjetos()**: Percorre uma lista de objetos de um bucket e salva todos eles localmente no diretório ArquivosS3

### 3. Main

```java
package school.sptech;

// Importe de classes próprias do projeto

import school.sptech.client.S3Provider;
import school.sptech.service.ExtractService;

// Importe de recursos do SDK da AWS
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Exception;

// Importe de recursos do próprio Java
import java.io.IOException;
import java.util.List;

// Ponto de entrada do programa
public class Main {
    public static void main(String[] args) {

        // Instanciando o cliente S3 através do S3Provider
        S3Provider provider = new S3Provider();
        S3Client s3Client = provider.getS3Client();

        // Instanciando o objeto ExtractService que manipula o bucket S3 através do client S3
        ExtractService s3 = new ExtractService(s3Client);

        // Salva o nome do bucket que será manipulado
        String nomeBucket = "s3-codenexus";

        try {
            // Cria uma lista de buckets  
            List<Bucket> listaBuckets = s3.listarBuckets();
            // Percorre essa lista buscando o bucket que deseja manipular
            for (Bucket bucket : listaBuckets) {
                if (bucket.name().equals(nomeBucket)) {
                    s3.setBucketName(nomeBucket); // Salva o nome do bucket na instância da classe ExtractService
                    break; // Finaliza o loop
                }
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao listar buckets: " + e.getMessage());
        }

        try {
            // Lista todos os objetos do bucket
            // Extrai (donwload) os objetos da lista  
            s3.extrairObjetos(s3.listarObjetos(nomeBucket));
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }
    }
}
```