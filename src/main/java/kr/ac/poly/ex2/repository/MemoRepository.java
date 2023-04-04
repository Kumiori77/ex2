package kr.ac.poly.ex2.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.ac.poly.ex2.entity.Memo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // select (범위 안의 mno 값 내림차순 정렬)
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long From, Long to);

    // select 패이징 처리 (범위 안의 mno 값 내림차순 정렬)
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    // delete (mno보다 작은 행 삭제)
    void deleteMemoByMnoLessThan(Long num);

    //@Query 어노테이션
    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText2(@Param("param") Memo memo);

    @Query(value = "select m from Memo m where m.mno > :mno",
    countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(@Param("mno") Long mno, Pageable pageable);

    @Query(value= "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno",
    countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(@Param("mno") Long mno, Pageable pageable);

    // native SQL
    @Query(value = "select * from tbl_memo where mno > 80", nativeQuery = true)
    List<Memo> getNativeResult();
//    List<Object[]> getNativeResult();

}
