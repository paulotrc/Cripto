Commit of refactoring:

PT-Br
Melhorias Feitas
    Separação de Responsabilidades: O código foi dividido em vários métodos que tratam de tarefas específicas, como getKeyPathFromUser, getDataFromUser, loadOrCreateKeyPair, loadKeyPair, createKeyPair, saveKeyToFile, encryptData e decryptData. Isso melhora a legibilidade e organização.
    Remoção de Variáveis Desnecessárias: Variáveis que não eram utilizadas ou que podiam ser calculadas na hora foram removidas.
    Tratamento de Exceções: Um único bloco try-catch envolve todo o fluxo de execução, em vez de múltiplos try-catch separando erros. Isso simplifica a estrutura do código.
    Uso de Recursos com Try-With-Resources: Para garantir que os streams de arquivos sejam fechados adequadamente, foi utilizado o recurso try-with-resources, o que evita potenciais vazamentos de recursos.
    Aprimoramento da Segurança: A chave RSA foi aumentada para 2048 bits, que é uma prática comum recomendada por questões de segurança. ¹
Essas melhorias tornam o código mais claro, facilitando a manutenção e a compreensão do fluxo lógico da aplicação.

EN
Improvements Made
    Separation of Responsibilities: The code has been divided into several methods that handle specific tasks, such as getKeyPathFromUser, getDataFromUser, loadOrCreateKeyPair, loadKeyPair, createKeyPair, saveKeyToFile, encryptData, and decryptData. This improves readability and organization.
    Removal of Unnecessary Variables: Variables that were not used or that could be calculated on the fly were removed.
    Exception Handling: A single try-catch block wraps the entire execution flow, instead of multiple try-catch separating errors. This simplifies the code structure.
    Using Resources with Try-With-Resources: To ensure that file streams are closed properly, the try-with-resources feature was used, which prevents potential resource leaks.
    Security Enhancement: The RSA key has been increased to 2048 bits, which is a common practice recommended for security reasons. ¹
These improvements make the code clearer, making it easier to maintain and understand the logical flow of the application.

PT-Br
1 - Apenas para efetuar testes, alterar o valor de referência de 2048 para 1024.

EN
1 - For testing purposes only, change the reference value from 2048 to 1024.
