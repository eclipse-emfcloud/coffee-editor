/** @type {import('eslint').Linter.Config} */
module.exports = {
    root: true,
    extends: ['prettier', './configs/base.eslintrc.json', './configs/warnings.eslintrc.json', './configs/errors.eslintrc.json'],
    ignorePatterns: ['**/{node_modules,lib}', 'plugins'],
    parserOptions: {
        tsconfigRootDir: __dirname,
        project: 'tsconfig.json'
    }
};
