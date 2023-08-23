package club.ovelya.socketsystem.dao;

import club.ovelya.socketsystem.entity.WebSocketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebSocketMessageRepository extends JpaRepository<WebSocketMessage, Integer> {

  //select * from web_socket_message where to_who='all' order by id desc limit 10 ;
}
