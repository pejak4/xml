import React from 'react';
import axios from '../axios-objects';
import './Messages.css';
class MessageReply extends React.PureComponent {

    state = {
        text: '',
        from: ''
    };

    componentDidMount= async() => {
        const resp = await axios.get('/authentication-service/getUserById',{params: {id: this.props.reply}});
        if(resp){
            this.setState({from: `${resp.data.firstName} ${resp.data.lastName}`});
        }
    }

    render(){
        return(
            <div className='containerr' >
                        <h4><b>Reply to : {this.state.from}</b></h4>
                        <textarea placeholder="Enter your message here.." cols="50" rows="15" onChange={(e) => {this.setState({text: e.target.value});}}/><br/>
                        <button className="button button3" onClick={() => {this.props.actionn(this.state.text, 'BACK')}}>Back</button>
                        <button className="button button1" onClick={() => {this.props.actionn(this.state.text, 'REPLY')}}>Reply</button>
                    </div>);
    }

}

export default MessageReply;