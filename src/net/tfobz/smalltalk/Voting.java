package net.tfobz.smalltalk;

public class Voting {
	private String line;
	private int[] votes;

	public Voting(String line) {
		this.line = line;
		votes = new int[4];
	}

	public void setVote(int vote) {
		votes[vote]++;
	}

	public String getVotingString() {
		String lh[] = new String[5];
		System.out.println("     " + line);
		this.line = line.substring(9);
		for (int i = 0; i < 5; i++) {
			int j = line.indexOf("///");
			lh[i] = line.substring(0, j);
			line = line.substring(j + 3);
		}

		return "=)(/&%$§!" + lh[0] + "///" + lh[1] + "///" + votes[0] + "///" + lh[2] + "///" + votes[1] + "///" + lh[3]
				+ "///" + votes[2] + "///" + lh[4] + "///" + votes[3];
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int[] getVotes() {
		return votes;
	}

	public void setVotes(int[] votes) {
		this.votes = votes;
	}

}
