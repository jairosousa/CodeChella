# CodeChella

Curso de Spring Boot oferecido pela Alura. Desta vez, nosso foco será em programação reativa usando o Spring WebFlux

## Instrutora
Jacqueline Oliveira, engenheira de software e instrutora na Alura.

## Conhecendo o projeto
Durante o desenvolvimento do projeto, criaremos uma API reativa para o projeto **CodeChella**, um site de eventos onde haverá vendas de ingressos. Nosso objetivo é que a propagação de mudanças ocorra em tempo real para a clientela a cada venda de ingresso. Assim, a pessoa que está na fila aguardando para comprar seu ingresso saberá em que posição está e se, eventualmente, o ingresso foi esgotado.

A ideia é proporcionar interação em tempo real com requisições assíncronas e não bloqueantes.

## O que aprenderemos?
Durante o curso, veremos as dependências necessárias para trabalhar de forma assíncrona. Teremos acesso ao banco de dados para persistência e busca de informações de forma assíncrona.

Também consumiremos uma API de tradução de textos de forma não bloqueante para não prejudicar nossa aplicação. Implementaremos testes assíncronos e utilizaremos a ferramenta Apache AB para comparar o desempenho de uma API reativa com uma API servlet.

## Pré-requisitos
É importante ter noções de Java, Programação Orientada a Objetos e Spring. Além disso, é essencial ter concluído os cursos listados como pré-requisitos, pois é necessário saber como construir uma API REST baseada em servlet, por exemplo.

Faremos comparações entre uma API servlet e nossa API reativa. Portanto, é fundamental se atentar aos pré-requisitos. Se você ainda não os concluiu, é recomendável fazer isso para garantir o melhor aproveitamento possível do curso.


## Capítulo 01 - Criando API Reativa

### Configurando o banco de dados no application.properties

#### Doker Run
docker run --name container-postgresql -e POSTGRES_DB=codechella -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin123 -p 5432:5432 -v dados-postgres:/var/lib/postgresql/data -d postgres
```bash
docker run --name container-postgresql -e POSTGRES_DB=codechella -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin123 -p 5432:5432 -v dados-postgres:/var/lib/postgresql/data -d postgres
````
Feito isso, é necessário alterar a configuração do Spring Boot para se conectar corretamente ao PostgreSQL que está sendo executado a partir do container. No seu projeto Spring Boot, abra o arquivo src/main/resources/application.properties e ajuste as seguintes configurações:

```properties

spring.r2dbc.url=r2dbc:postgresql://localhost:5432/codechella
spring.r2dbc.username=usuario
spring.r2dbc.password=senha

spring.flyway.url=jdbc:postgresql://localhost:5432/codechella
spring.flyway.user=usuario
spring.flyway.password=senha

```

### Preparando o ambiente: script para criação das migrations

Ao criar o arquivo com a nomenclatura `V001__create_table_eventos.sql dentro da pasta resources\db.migration`, você deverá colocar o seguinte código:

```sql
create table eventos(
    id bigserial not null,
    tipo varchar(30) not null,
    nome varchar(100) not null,
    data date,
    descricao varchar(200) not null,

    primary key(id)
);
```

Ao criar o segundo arquivo, denominado V002__insert_table_eventos.sql, usaremos o código abaixo:

```sql
INSERT INTO eventos (tipo, nome, data, descricao) VALUES
('SHOW', 'Taylor Swift', '2024-02-15', 'Um evento imperdível para todos os amantes da música pop.'),
('TEATRO', 'A Comédia da Vida', '2024-07-20', 'Uma peça teatral emocionante que retrata com humor momentos complexos da vida. Você não pode perder.'),
('CONCERTO', 'Concerto de Verão', '2024-06-10', 'Um concerto ao ar livre com grandes nomes da música clássica.'),
('PALESTRA', 'Palestra sobre Inteligência Artificial', '2024-09-05', 'Uma palestra informativa sobre as últimas tendências e ferramentas de IA.'),
('WORKSHOP', 'Workshop de Fotografia', '2024-11-30', 'Um workshop prático para aprimorar suas habilidades.'),
('SHOW', 'Stand-up Comedy', '2024-03-18', 'Uma noite de muito riso e diversão com os melhores comediantes do Brasil.'),
('CONCERTO', 'O Fantasma da Ópera', '2024-12-25', 'Uma apresentação clássica de ópera.'),
('TEATRO', 'Festival de Teatro', '2025-04-12', 'Um festival com as melhores peças teatrais do ano.'),
('PALESTRA', 'Fórum de Inovação e Empreendedorismo', '2025-10-08', 'Um fórum que reúne os maiores inovadores do país. Presença dos apresentadores do Shark Tank.'),
('WORKSHOP', 'Aulas de Culinária', '2025-05-20', 'Aulas práticas de culinária com chefs renomados.'),
('SHOW', 'Bruno Mars', '2025-05-22', 'De volta ao Brasil, Bruno promete entregar o maior e melhor show de sua carreira na turnê This is Mars.'),
('TEATRO', 'A Comédia da Vida', '2025-07-14', 'Uma peça teatral emocionante que você não pode perder.'),
('SHOW', 'The Weeknd', '2025-11-02', 'Um show eletrizante ao ar livre com muitos efeitos especiais.'),
('WORKSHOP', 'Café com Ideias', '2025-01-25', 'Um evento que ensina técnicas de brainstorming e priorização para alavancar projetos.');
```

