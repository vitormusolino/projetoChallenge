# 📦 Sistema de Estoque Laboratorial

Este projeto está sendo desenvolvido para o **Challenge Anual da DASA (FIAP)**.  
O objetivo é criar uma solução completa de **gestão de estoque laboratorial**, que facilite o controle de insumos, reagentes, equipamentos e demais itens utilizados em laboratórios.

---

## 🚀 Tecnologias Utilizadas

- **Java** → Back-end e regras de negócio  
- **Oracle SQL** → Banco de dados relacional  
- **Front-end (em desenvolvimento)** → Interface amigável para interação com o sistema  

---

## 🧪 Funcionalidades Principais (Planejadas / Implementadas)

✔ Cadastro de produtos e reagentes  
✔ Controle de entrada e saída de estoque  
✔ Atualização automática de quantidades  
✔ Relatórios de movimentação  
⬜ Interface gráfica para visualização dos dados (em desenvolvimento)  
⬜ Controle de usuários e permissões  

---

## 📂 Estrutura do Projeto

```bash
projeto-estoque-laboratorial/
│── sql/                # Scripts de criação e inserção no banco Oracle
│── src/                # Código fonte em Java
│   ├── model/          # Classes de entidades (Produto, Estoque, etc.)
│   ├── dao/            # Classes de acesso ao banco de dados (DAO)
│   ├── service/        # Regras de negócio
│   └── main/           # Classe principal
│── frontend/           # (futuro) Aplicação front-end
│── README.md           # Documentação do projeto
