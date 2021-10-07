/** @type {import('eslint').Linter.Config} */
module.exports = {
    root: true,
    extends: [
        '../../web/configs/base.eslintrc.json',
        '../../web/configs/warnings.eslintrc.json',
        '../../web/configs/errors.eslintrc.json'
    ],
    ignorePatterns: [
        '**/{node_modules,lib}'
    ],
    parserOptions: {
        tsconfigRootDir: __dirname,
        project: 'tsconfig.json'
    }
};
