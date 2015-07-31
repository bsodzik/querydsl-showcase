# querydsl-showcase
Small comparison of JPQL vs JPA vs Querydsl. Before digging into the code you may want to get familiar with short [presentation](https://docs.google.com/presentation/d/1OfHMqn2re_v2FZ0i5yrSpJExx1td_UptcTHTmMxbmn0/edit?usp=sharing).

Project contains three different implementations of five common SQL queries
- select with order by and pagination - `CustomerRepository#findAll`
- select with where condition - `CustomerRepository#findByBirthDate`
- select with group by and aggregation function - `CustomerRepository#countByCustomerType`
- select with join, group by and aggregation function - `CustomerRepository#calculateCustomersBalance`
- select with join, group by, having and aggregation function - `CustomerRepository#findCustomersHavingMultipleAccounts`

To validate implemenation correctness the same unit tests are executed for each implementation.
