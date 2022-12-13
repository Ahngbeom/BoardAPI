package com.board.api.dummy;

import com.board.api.dto.PostDTO;
import com.board.api.service.post.PostCRUD_Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"file:web/WEB-INF/dispatcher-servlet.xml", "file:web/WEB-INF/securityContext.xml"})
@WithUserDetails(value = "admin")
class BoardPosts {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private PostCRUD_Service service;


    @BeforeEach
    void setUp() {
        assertNotNull(logger);
        assertNotNull(service);
    }

    @Test
    void registerPostsDummy() {
        for (int i = 1; i <= 10; i++) {
            PostDTO board = new PostDTO("normal", "TEST " + i, "TEST " + i);
            service.registerPost(board);
//            logger.info(service.getBoardByBno(board.getBno()));
            logger.info(board);
        }
    }

    @Test
    void registerPostsDummyForSearch() {
        service.registerPost(new PostDTO(
                "normal",
                "The standard Lorem Ipsum passage, used since the 1500s"
                ,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                )
        );
        service.registerPost(new PostDTO(
                "normal",
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC"
                ,
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
                )
        );
        service.registerPost(new PostDTO(
                "normal",
                "1914 translation by H. Rackham"
                ,
                "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"
                )
        );
    }

    @Test
    void removeAllPosts() {
        logger.info(service.removeAllPost());
    }
}