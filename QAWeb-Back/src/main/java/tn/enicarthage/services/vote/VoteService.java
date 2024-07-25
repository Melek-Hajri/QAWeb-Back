package tn.enicarthage.services.vote;

import java.io.IOException;

import tn.enicarthage.dtos.VoteDTO;

public interface VoteService {

	VoteDTO addVote(VoteDTO voteDTO) throws IOException;
}
