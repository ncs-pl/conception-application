# Configurer IntelliJ

Pour profiter de toutes les fonctionnalités du projet durant le développement
dans IntelliJ, il est nécessaire de configurer quelques paramètres ne pouvant
pas être partagés.

## Google Java Format

Pour que le formatage du code soit automatiquement appliqué, il est nécessaire
d'installer le plugin `google-java-format` dans IntelliJ.

Ensuite, il faut cliquer sur `Help` (Aide) -> `Edit Custom VM Options...`
(Modifier les options VM personnalisées...) et ajouter ceci à la suite :

```shell
--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED
--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```

Pensez à regarder dans vos paramètres si `google-java-format` est bien activé
(dans les paramètres de l'éditeur, dans `Other Settings` -> `Google Java
Format`).