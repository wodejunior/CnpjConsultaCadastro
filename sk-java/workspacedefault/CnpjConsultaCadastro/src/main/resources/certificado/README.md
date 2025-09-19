# Pasta de Certificados

Esta pasta é destinada aos certificados A1 (.p12 ou .pfx) para autenticação na SEFAZ.

## Como usar:

1. Coloque seu certificado A1 nesta pasta
2. Renomeie o arquivo para `certificado.p12` (ou mantenha o nome original)
3. O sistema carregará automaticamente o certificado desta pasta

## Arquivos suportados:
- `certificado.p12` (nome padrão)
- `certificado.pfx` (nome alternativo)
- Qualquer arquivo .p12 ou .pfx nesta pasta

## Segurança:
⚠️ **IMPORTANTE**: 
- Nunca commite certificados no controle de versão
- Mantenha a senha do certificado segura
- Esta pasta deve estar no .gitignore

## Exemplo de estrutura:
```
resources/certificado/
├── README.md (este arquivo)
├── certificado.p12 (seu certificado A1)
└── homologacao.p12 (certificado de testes - opcional)
```

