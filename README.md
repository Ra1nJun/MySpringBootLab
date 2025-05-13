### [실습2-4] Spring Boot와 JPA(Java Persistence API) 활용
* Entity,Repository,RepositoryTest
* Book and BookDetail 1:1 연관 관계
  * FetchType.LAZY vs FetchType.EAGER
  * @JoinColumn, mappedBy
  * 연관 관계의 주인(owner) 과 종속(non-owner)
  * Owner(BookDetail), Non-Owner(Book)
  * FK(외래키) 가지고 있는 쪽이 주인(owner)이다.
* Service
* DTO(Data Transfer Object)
* Controller