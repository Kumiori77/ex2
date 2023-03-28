package kr.ac.poly.ex2.repository;


import jakarta.transaction.Transactional;
import kr.ac.poly.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemorepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {

        System.out.println(memoRepository.getClass().getName());
    }

    // insert
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample ..." + i).build();

            memoRepository.save(memo); // insert문
        });
    }

    @Test
    public void testSelect() {
        Long mno = 101L; // 자료형의 메치를 위해 L 붙여줌

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("====================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
            System.out.println(result);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100L; // 자료형의 메치를 위해 L 붙여줌4

        Memo memo = memoRepository.getOne(mno);

        System.out.println("====================================");

        System.out.println(memo);
    }

    @Test
    public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("Updated Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {

        Long mno = 100L;

        memoRepository.deleteById(mno);

    }

    @Test
    public void testPageDefault() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("-------------------------------");

        System.out.println("Total Pages : " + result.getTotalPages());

        System.out.println("Total Count : " + result.getTotalElements());

        System.out.println("Page Number : " + result.getNumber());

        System.out.println("Page Size : " + result.getSize());

        System.out.println("Has Next Page? : " + result.hasNext());

        System.out.println("First Page? : " + result.isFirst());

        System.out.println("================================");

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

    }

    @Test
    public void testQueryMethod() {
        List<Memo> memoList = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : memoList) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethod2() {
        Pageable pageable = PageRequest.of(0, 10,Sort.by("Mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(60L, 70L, pageable);
//        result.get().forEach(memo -> {
//            System.out.println(memo);
//        });

        for (Memo memo : result) {
            System.out.println(memo.getMemoText());
        }

    }

    @Test
    @Commit
    @Transactional
    public void testDeleteQueryMehod() {

        memoRepository.deleteMemoByMnoLessThan(10L);

    }

    @Test
    public void testGetListDec() {
        List<Memo> memoList = memoRepository.getListDesc();

        for (Memo memo : memoList) {
            System.out.println(memo);
        }
    }

    @Test
    public void testupdateMemoText() {
        int updateCount = memoRepository.updateMemoText(10L, "changed Text");

        Optional<Memo> memo = memoRepository.findById(10L);

        System.out.println(memo.get());

    }

    @Test
    public void testUpdateMemoText2() {
        Memo memo = new Memo();
        memo.setMno(11L);
        memo.setMemoText("Changed Text2");

        int updateCount = memoRepository.updateMemoText2(memo);

        Optional<Memo> result = memoRepository.findById(11L);
        System.out.println(result.get());


    }

    @Test
    public void testGetListWidthQuery() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.getListWithQuery(80L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }


}
