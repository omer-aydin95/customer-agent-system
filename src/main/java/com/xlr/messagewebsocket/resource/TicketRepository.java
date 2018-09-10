package com.xlr.messagewebsocket.resource;

import com.xlr.messagewebsocket.model.Ticket;
import com.xlr.messagewebsocket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface repository for database queries for ticket entity
 *
 * Table name is tickets
 *
 * The queries are being determined via methods' names
 **/
public interface TicketRepository extends JpaRepository<Ticket, Long>
{
  /**
   * The method runs the following query
   *
   * select * from tickets assigned_at != null
   **/
  List<Ticket> findByAssignedAtIsNotNull();

  /**
   * The method runs the following query
   *
   * select * from tickets closed_at != null
   **/
  List<Ticket> findByClosedAtIsNotNull();

  /**
   * The method runs the following query
   *
   * select * from users, tickets where users.id = {assignee.id} and users.id = tickets.assignee_id and closed_at = null
   **/
  List<Ticket> findByAssigneeAndClosedByIsNull(User assignee);

  /**
   * The method runs the following query
   *
   * select * from users, tickets where users.id = {closedBy.id} and users.id = tickets.closed_by_id
   **/
  List<Ticket> findByClosedBy(User closedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where assignee_id = null and closed_by_id = null
   **/
  List<Ticket> findByAssigneeIsNullAndClosedByIsNull();

  /**
   * The method runs the following query
   *
   * select * from tickets where assignee_id != null and closed_by_id = null
   **/
  List<Ticket> findByAssigneeIsNotNullAndClosedByIsNull();

  /**
   * The method runs the following query
   *
   * select * from tickets where closed_by_id != null
   **/
  List<Ticket> findByClosedByIsNotNull();

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy} and closed_by_id = null
   **/
  List<Ticket> findByOpenedByAndClosedByIsNull(String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy} and assignee_id != null and closed_by_id = null
   **/
  List<Ticket> findByOpenedByAndAssigneeIsNotNullAndClosedByIsNull(String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy} and closed_by_id != null
   **/
  List<Ticket> findByOpenedByAndClosedByIsNotNull(String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy}
   **/
  List<Ticket> findByOpenedBy(String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy} and created_at between {startDate} and {endDate}
   **/
  List<Ticket> findByCreatedAtBetweenAndOpenedBy(LocalDateTime startDate, LocalDateTime endDate, String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where created_at between {startDate} and {endDate}
   **/
  List<Ticket> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy} and assigned_at between {startDate} and {endDate}
   **/
  List<Ticket> findByAssignedAtBetweenAndOpenedBy(LocalDateTime startDate, LocalDateTime endDate, String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where assigned_at between {startDate} and {endDate}
   **/
  List<Ticket> findByAssignedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

  /**
   * The method runs the following query
   *
   * select * from tickets where opened_by = {openedBy} and closed_at between {startDate} and {endDate}
   **/
  List<Ticket> findByClosedAtBetweenAndOpenedBy(LocalDateTime startDate, LocalDateTime endDate, String openedBy);

  /**
   * The method runs the following query
   *
   * select * from tickets where closed_at between {startDate} and {endDate}
   **/
  List<Ticket> findByClosedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
