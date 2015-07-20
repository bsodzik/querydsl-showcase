# querydsl-showcase
Small comparison of JPQL vs JPA vs Querydsl

Project contains three different implementations of five common SQL queries
- select with order by and pagination - `CustomerRepository#findAll`
- select with where condition - `CustomerRepository#findByBirthDate`
- select with group by and aggregation function - `CustomerRepository#countByCustomerType`
- select with join, group by and aggregation function - `CustomerRepository#calculateCustomersBalance`
- select with join, group by, having and aggregation function - `CustomerRepository#findCustomersHavingMultipleAccounts`

To validate implemenation correctness the same unit tests are executed for each implementation.
