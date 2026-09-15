# Project Instructions

## Purpose

This project is a focused Spring Boot learning sandbox for **MapStruct with JPA**. The goal is to explore correct and incorrect mapping scenarios between entities and DTOs, especially where persistence state, transactions, lazy loading, and session boundaries can produce subtle bugs.

AI agents working in this repository should optimize for **correctness over convenience** and treat the project as a place to surface, reproduce, and document mapping corner cases rather than to hide them.

## Technical Context

- **Stack**: Spring Boot, Java 21, Spring MVC, Spring Data JPA
- **Build**: Maven
- **Testing style**: integration-first where behavior depends on persistence context, transactions, proxies, or lazy associations
- **Database testing**: Testcontainers with PostgreSQL
- **Domain focus**: mapping managed and unmanaged JPA entities to DTOs and mapping DTOs back to entities with MapStruct

## What AI Agents Should Optimize For

1. Preserve realistic JPA behavior. Do not replace real persistence behavior with mocks when the point of the code is entity state or transaction semantics.
2. Prefer integration tests for persistence and mapping edge cases.
3. Make entity lifecycle assumptions explicit: **managed**, **detached**, **new/transient**, or **removed**.
4. Keep mapping logic deterministic and easy to reason about.
5. Expose corner cases instead of silently working around them.

## Mapping Rules

### General

- Prefer **explicit mappings** over implicit magic when the intent is not obvious.
- Keep MapStruct mappings readable and narrow in responsibility.
- Avoid putting business logic into mapper methods unless the mapper is explicitly designed for that purpose.
- Reuse shared mapper configuration if the project introduces common conventions later.

### JPA-Specific

- Always consider whether the source entity is still attached to an active persistence context.
- Be careful when mapping lazy associations; mapping must not accidentally trigger uncontrolled graph loading unless that behavior is intentional and covered by tests.
- Do not assume collections or nested relations are initialized.
- When mapping DTO back to entity, distinguish clearly between:
  - creating a new entity,
  - updating an existing managed entity,
  - reconstructing a detached entity graph.
- Avoid patterns that overwrite managed entity state in ways that break dirty checking or orphan handling unless that behavior is explicitly intended and tested.
- Be cautious with bidirectional associations, entity identity, equals/hashCode interactions, and partial updates.

## Testing Rules

- Use **Spring Boot integration tests** for mapper behavior that depends on:
  - real transactions,
  - Hibernate/JPA session boundaries,
  - lazy loading,
  - flush/clear cycles,
  - managed vs detached entities.
- Prefer **Testcontainers PostgreSQL** over in-memory substitutes when verifying persistence-related mapping behavior.
- Tests should make the persistence boundary obvious, for example by using flush/clear or separate transactional steps when relevant.
- Add tests for both the **expected behavior** and the **failure/gotcha scenario** when the lesson is important.
- Name tests so the JPA state and mapping expectation are clear.

## Change Guidelines for AI Agents

- Keep examples small and focused on one mapping concern at a time.
- Do not “fix” away educational failure cases; if a scenario is intentionally problematic, document it and test it clearly.
- If adding a mapper, also consider whether the repository needs:
  - DTO fixtures,
  - entity fixtures,
  - integration coverage for session/transaction behavior.
- If adding update mappings, ensure null-handling and identity-handling are explicit.
- If a change touches entity relationships, verify mapping behavior for nested objects and collections.

## Documentation Expectations

- When a new corner case is introduced, document:
  - the scenario,
  - why it is tricky,
  - what behavior is expected,
  - how it is covered by tests.
- Prefer short, practical explanations tied to code and tests over generic theory.

## Anti-Patterns to Avoid

- Replacing integration coverage with mocks for JPA lifecycle problems
- Silent lazy-loading side effects hidden inside mapping
- Broad catch blocks that mask mapping or transaction failures
- DTO-to-entity mapping that ignores entity identity and persistence state
- Large object graph mapping without explicit intent
- “Convenience” changes that make the demo less realistic

## Default Agent Workflow

When making changes in this repository, AI agents should usually:

1. Identify whether the scenario is about pure field mapping or persistence-context behavior.
2. Inspect related entities, DTOs, mappers, and tests before editing.
3. Prefer focused integration tests for JPA/MapStruct edge cases.
4. Keep mapper changes minimal and explicit.
5. Update this documentation when the project gains a new class of mapping gotcha or repository convention.

## Definition of a Good Contribution

A good change in this project:

- teaches something about MapStruct + JPA behavior,
- keeps persistence semantics realistic,
- is covered by tests when behavior is non-trivial,
- makes mapper intent clearer,
- does not hide corner cases that the project exists to study.
