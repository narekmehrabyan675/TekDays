package com.tekdays

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class EventService {

    List<TekEvent> search1(String query) {
        if (!query) return TekEvent.list()

        String likeQuery = "%${query}%"

        /*def results = TekEvent.createCriteria().listDistinct {
            or {
                    ilike('city', likeQuery)
                    ilike('name', likeQuery)
                    ilike('venue', likeQuery)
                    ilike('description', likeQuery)

                //Via user
                organizer(joinType: 'left') {
                    or {
                        ilike('fullName', likeQuery)
                        ilike('userName', likeQuery)
                        ilike('email', likeQuery)
                        ilike('website', likeQuery)
                        ilike('bio', likeQuery)

                    }
                }

                // Via volunteers (TekUser)
                volunteers{
                    or {
                        ilike('fullName', likeQuery)
                        ilike('userName', likeQuery)
                        ilike('email', likeQuery)
                        ilike('website', likeQuery)
                        ilike('bio', likeQuery)
                    }
                }



                // Via sponsorships (Sponsorship)
                sponsorships {
                    or {
                        ilike('contributionType', likeQuery)
                        ilike('description', likeQuery)
                        ilike('notes', likeQuery)

                        sponsor{
                            or {
                                ilike('name', likeQuery)
                                ilike('website', likeQuery)
                                ilike('description', likeQuery)
                            }
                        }
                    }
                }

                // Via tasks (Task)
                tasks{
                    or {
                        ilike('title', likeQuery)
                        ilike('notes', likeQuery)

                        assignedTo{
                            or {
                                ilike('fullName', likeQuery)
                                ilike('userName', likeQuery)
                                ilike('email', likeQuery)
                                ilike('website', likeQuery)
                                ilike('bio', likeQuery)
                            }
                        }
                    }
                }

                //  messages (TekMessage)
                messages {
                    or {
                        ilike('subject', likeQuery)
                        ilike('content', likeQuery)

                        author {
                            or {
                                ilike('fullName', likeQuery)
                                ilike('userName', likeQuery)
                                ilike('email', likeQuery)
                                ilike('website', likeQuery)
                                ilike('bio', likeQuery)
                            }
                        }

                    }
                }

            }
        }*/
        def results = TekEvent.createCriteria().listDistinct {
            createAlias('organizer', 'o', CriteriaSpecification.LEFT_JOIN)
            createAlias('volunteers', 'v', CriteriaSpecification.LEFT_JOIN)
            createAlias('sponsorships', 's', CriteriaSpecification.LEFT_JOIN)
            createAlias('s.sponsor', 'sp', CriteriaSpecification.LEFT_JOIN)
            createAlias('tasks', 't', CriteriaSpecification.LEFT_JOIN)
            createAlias('t.assignedTo', 'ta', CriteriaSpecification.LEFT_JOIN)
            createAlias('messages', 'm', CriteriaSpecification.LEFT_JOIN)
            createAlias('m.author', 'ma', CriteriaSpecification.LEFT_JOIN)

            or {
                ilike('city', likeQuery)
                ilike('name', likeQuery)
                ilike('venue', likeQuery)
                ilike('description', likeQuery)

                or {
                    ilike('o.fullName', likeQuery)
                    ilike('o.userName', likeQuery)
                    ilike('o.email', likeQuery)
                    ilike('o.website', likeQuery)
                    ilike('o.bio', likeQuery)
                }

                or {
                    ilike('v.fullName', likeQuery)
                    ilike('v.userName', likeQuery)
                    ilike('v.email', likeQuery)
                    ilike('v.website', likeQuery)
                    ilike('v.bio', likeQuery)
                }

                or {
                    ilike('s.contributionType', likeQuery)
                    ilike('s.description', likeQuery)
                    ilike('s.notes', likeQuery)
                    ilike('sp.name', likeQuery)
                    ilike('sp.website', likeQuery)
                    ilike('sp.description', likeQuery)
                }

                or {
                    ilike('t.title', likeQuery)
                    ilike('t.notes', likeQuery)
                    ilike('ta.fullName', likeQuery)
                    ilike('ta.userName', likeQuery)
                    ilike('ta.email', likeQuery)
                    ilike('ta.website', likeQuery)
                    ilike('ta.bio', likeQuery)
                }

                or {
                    ilike('m.subject', likeQuery)
                    ilike('m.content', likeQuery)
                    ilike('ma.fullName', likeQuery)
                    ilike('ma.userName', likeQuery)
                    ilike('ma.email', likeQuery)
                    ilike('ma.website', likeQuery)
                    ilike('ma.bio', likeQuery)
                }
            }
        }


        return results
    }
}

