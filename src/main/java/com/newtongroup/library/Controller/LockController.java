package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.LibraryCardRepository;
import com.newtongroup.library.Repository.LockRepository;
import com.newtongroup.library.Repository.UnlockRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lock")
public class LockController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryCardRepository libraryCardRepository;

    @Autowired
    LockRepository lockRepository;

    @Autowired
    UnlockRepository unlockRepository;

    @RequestMapping("/")
    private String returnDoLock(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        List<LibraryCard> libraryCardsList = libraryCardRepository.findAll()
                .stream()
                .filter(card -> card.getVisitor().isActive())
                .collect(Collectors.toList());

        List<Lock> locksList = lockRepository.findAll();
        List<Unlock> unlocksList = unlockRepository.findAll();

        theModel.addAttribute("libraryCards", libraryCardsList);
        theModel.addAttribute("locks", locksList);
        theModel.addAttribute("unlocks", unlocksList);
        theModel.addAttribute("libraryCard", new LibraryCard());
        theModel.addAttribute("lock", new Lock());
        theModel.addAttribute("unlock", new Unlock());

        return "lock/lock-register";
    }
    
    @RequestMapping("/doLock")
    public String Lock(@ModelAttribute("lock") Lock lock,
                       @ModelAttribute("libraryCard") LibraryCard libraryCard,
                       Model theModel, Principal principal) {

        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        Long cardID = libraryCard.getLibraryCardNumber();
        LibraryCard tempLibraryCard = libraryCardRepository.getOne(cardID);
        Visitor visitor = tempLibraryCard.getVisitor();

        if (tempLibraryCard == null) {
            return "error/not-valid-cardnumber";
        }
        if (lock == null) {
            return "error/not-valid-cause";
        }

        if (!libraryCard.isActive()) {
            return "error/not-valid-card-already-locked";
        } else {
            setCardToFalseAndSetLock(tempLibraryCard, lock);
            theModel.addAttribute("libraryCardNumber", cardID);
            return "lock/lock-succesfull";
        }
    }

    @RequestMapping("/doUnlock")
    public String Unlock(@ModelAttribute("unlock") Unlock unlock,
                         @ModelAttribute("libraryCard") LibraryCard libraryCard,
                         Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        Long cardID = libraryCard.getLibraryCardNumber();
        LibraryCard tempLibraryCard = libraryCardRepository.getOne(cardID);


        if (libraryCard.isActive()) {
            return "error/not-valid-card-already-unlocked";
        } else {
            setCardToTrueAndSetUnlock(tempLibraryCard, unlock);
            theModel.addAttribute("libraryCardNumber", cardID);
            return "lock/unlock-successful";
        }
    }

    private void setCardToTrueAndSetUnlock(LibraryCard libraryCard, Unlock unlock) {
        libraryCard.setActive(true);
        libraryCard.setUnlock(unlock);
        libraryCard.setLock(null);


        libraryCardRepository.save(libraryCard);
    }

    private void setCardToFalseAndSetLock(LibraryCard libraryCard, Lock lock) {
        libraryCard.setActive(false);
        libraryCard.setLock(lock);
        libraryCard.setUnlock(null);


        libraryCardRepository.save(libraryCard);
    }
}
